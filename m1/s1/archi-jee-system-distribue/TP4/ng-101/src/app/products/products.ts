import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product';
import { JsonPipe } from '@angular/common';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-products',
  imports: [CommonModule],
  standalone: true,
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class ProductsComponent implements OnInit {
  public products: any;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts() {
    this.productService.getAllProducts().subscribe({
      next: (data: any) => {
        this.products = data;

        console.log({ products: this.products });
      },
      error: (err: any) => {
        console.log(err);
      },
    });
  }

  handleDelete(p: any) {
    let conf = confirm('Êtes-vous sûr de vouloir supprimer ?');
    if (conf) {
      this.productService.deleteProduct(p.id).subscribe({
        next: () => {
          this.getProducts();
        },
        error: (err: any) => {
          console.log(err);
        },
      });
    }
  }
}
