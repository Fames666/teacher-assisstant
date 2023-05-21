export class Journal {

  constructor(public readonly id: number,
              public readonly classNumber: number,
              public readonly classLetter: string,
              public readonly academicSemester: AcademicSemester,
              public readonly leadTeacher: string,
              public readonly rows: JournalRow[]) { }
}

export class JournalRow {

  constructor(public readonly journalId: number,
              public readonly student: StudentData,
              public readonly averageMark: number,
              public readonly progressLevel: number,
              public readonly remarksAmount: number,
              public readonly notAttested: boolean,
              public readonly notAttestedReason: string | undefined,
              public readonly cells: JournalCell[]) { }
}

export class JournalCell {

  constructor(public readonly date: string,
              public readonly highlightType: string,
              public mark: number | undefined,
              public readonly uncertain: boolean,
              public readonly uncertaintyReason: string | undefined) { }
}

export class AcademicSemester {

  constructor(public readonly year: number,
              public readonly semester: number,
              public readonly startDate: string,
              public readonly endDate: string,
              public readonly partitions: AcademicSemesterPartition[]) { }
}

export class AcademicSemesterPartition {

  constructor(public readonly month: string,
              public readonly daysOfWeek: DayOfWeek[]) { }
}

export class StudentData {

  constructor(public readonly id: string,
              public readonly fullName: string) { }
}

export type DayOfWeek = { (day: string): string };
