import { Component, Input, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: '[element=message-box]',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.css']
})
export class MessageBoxComponent implements OnInit {

  @Input() public changeBoxNotifier?: Subject<string>

  public ngOnInit(): void {
    this.changeBoxNotifier?.subscribe((box: string) => this.onBoxChanged(box));
  }

  public onBoxChanged(box: string): void {
    if (box === 'sent') {
      this.currentInbox = this.messagesSent;
    } else if (box === 'inbox') {
      this.currentInbox = this.messagesInbox;
    }
  }

  private readonly messagesInbox = [
    {
      avatar: '../../assets/images/avatar.jpg',
      name: 'Валентина Вахтерова',
      tag: 'Рабочие',
      message: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry...',
      datetime: '2ч назад'
    },
    {
      avatar: 'http://flatfull.com/themes/note/images/avatar_default.jpg',
      name: 'Петр Сидоров',
      tag: null,
      message: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry...',
      datetime: '2023-05-15 15:22'
    },
    {
      avatar: '../../assets/images/avatar.jpg',
      name: 'Александра Швец',
      tag: null,
      message: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry...',
      datetime: '2023-05-14 11:20'
    }
  ];

  private readonly messagesSent = [
    {
      avatar: 'http://flatfull.com/themes/note/images/avatar_default.jpg',
      name: 'Teacher Test',
      tag: null,
      message: 'Здравствуй, Аледксандр!',
      datetime: 'только что'
    }
  ];

  public currentInbox = this.messagesInbox;
}
