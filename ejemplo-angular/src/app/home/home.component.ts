import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Post, PostsService} from '../shared/posts.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor(private postsService: PostsService) {}
  posts: Post[];

  ngOnInit(): void {
    this.posts = this.postsService.getAllPosts();
  }

  onSubmit(form: NgForm) {
    const title = form.value.title;
    const body = form.value.body;

    this.postsService.createPost(title, body);
    this.posts = this.postsService.getAllPosts();
    form.reset();
  }
}
