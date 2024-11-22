import { Component, OnInit } from '@angular/core';
import { BooksService } from '../books.service'

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
})
export class BookListComponent implements OnInit {
  books: any[] = [];

  constructor(private booksService: BooksService) {}

  ngOnInit(): void {
    this.booksService.getBooks().subscribe((data) => {
      this.books = data;
    });
  }
}
