import {Injectable} from '@angular/core';

export interface Post {
  userId: number;
  id: number;
  title: string;
  body: string;
}

@Injectable({
  providedIn: 'root'
})
export class PostsService {
  constructor() {}

  posts: Post[] = [
    {
      id: 1,
      userId: 1,
      body: 'body post 1',
      title: 'title post 1'
    },
    {
      id: 2,
      userId: 2,
      body: 'body post 2',
      title: 'title post 2'
    },
    {
      id: 3,
      userId: 3,
      body: 'body post 3',
      title: 'title post 3'
    }
  ];

  getAllPosts(): Post[] {
    return this.posts;
  }

  createPost(title: string, body: string): void {
    const newId = this.posts.length + 1;
    const newPost: Post = {
      id: newId,
      body: body,
      title: title,
      userId: newId
    };
    this.posts.push(newPost);
  }
}
