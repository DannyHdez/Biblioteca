import { Component } from '@angular/core';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss'],
})
export class UserHomeComponent {
  username = 'User Name'; // Replace with real data
  userBooks = [
    { title: 'User Book 1', description: 'Description for user book 1' },
    { title: 'User Book 2', description: 'Description for user book 2' },
    { title: 'User Book 3', description: 'Description for user book 3' },
  ];

  selectedBook: any = null;

  selectBook(book: any): void {
    this.selectedBook = book;
  }

  closeBook(event: MouseEvent): void {
    event.stopPropagation();
    this.selectedBook = null;
  }
}
