import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  books = [
    {
      id: 1,
      title: 'El Quijote',
      cover_url: 'assets/images/books/book1.avif',
      description: 'Una historia clásica de aventuras y caballería.',
    },
    {
      id: 2,
      title: 'Moby Dick',
      cover_url: 'assets/images/books/book2.avif',
      description: 'La emocionante caza de la gran ballena blanca.',
    },
    {
      id: 3,
      title: 'La Odisea',
      cover_url: 'assets/images/books/book3.avif',
      description: 'La épica historia del viaje de Odiseo a casa.',
    },
  ];

  selectedBook: any = null;

  selectBook(book: any): void {
    this.selectedBook = book;
  }

  closeBookDetails(): void {
    this.selectedBook = null;
  }
}
