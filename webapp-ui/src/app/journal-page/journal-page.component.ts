import { Component, OnInit } from '@angular/core';
import { AcademicSemester, AcademicSemesterPartition, Journal, JournalCell, JournalRow, StudentData } from './model/journal-page.models';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';

@Component({
  selector: '[page=journal]',
  templateUrl: './journal-page.component.html',
  styleUrls: ['./journal-page.component.css']
})
export class JournalPageComponent implements OnInit {

  private readonly URL = "http://localhost:8080/journals/2483";

  public journal?: Journal;
  public students?: StudentData[];
  public journalRows?: JournalRow[];
  public academicSemester?: AcademicSemester;
  public academicSemesterPartitions?: AcademicSemesterPartition[];

  public readonly cellClickedObservable: Subject<string>;

  private selectedCellId?: string;

  constructor(private readonly httpClient: HttpClient) {
    this.cellClickedObservable = new Subject<string>();
  }

  public ngOnInit(): void {
    this.httpClient.get<Journal>(this.URL)
        .subscribe(response => this.onJournalLoaded(response));
  }

  public onJournalLoaded(journal: Journal): void {
    this.journal = journal;
    this.journalRows = journal.rows;
    this.students = journal.rows.map(row => row.student);
    this.academicSemester = journal.academicSemester;
    this.academicSemesterPartitions = journal.academicSemester.partitions;
  }

  public onCellClicked(cellId: string): void {
    this.selectedCellId = cellId;
    this.cellClickedObservable.next(cellId);
  }

  public onMarkConfirmed(mark: number): void {
    let split = this.selectedCellId?.split(' ') ?? ['', ''];
    this.httpClient.put<JournalRow>(this.URL, null, {
      params: {
        "studentId": split[0],
        "date": split[1],
        "mark": mark,
        "action": "put-mark"
      }
    }).subscribe(response => this.onMarkSet(response));
  }

  private onMarkSet(response: JournalRow): void {
    let responseCell = response.cells[0];
    let row = this.journalRows?.find(row => row.student.id === response.student.id);
    let cell = row?.cells.find(cell => cell.date === responseCell.date);
    if (cell !== undefined) {
      cell.mark = responseCell.mark;
    }
  }

  public extractJournalCells(studentId: string): JournalCell[] {
    return this.journalRows?.filter(row => row.student.id === studentId)
                            .flatMap(row => row.cells)
        ?? [];
  }
}
