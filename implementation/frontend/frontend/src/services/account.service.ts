import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {User} from "../app/models/user";
import {environment} from "../environments/environment";
import {UserRegistration} from "../app/models/userRegistration";

//import { environment } from '@environments/environment';
//import { User } from '@app/_models';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;
  private apiGateway: string;

  constructor(
    private router: Router,
    private http: HttpClient
  ) {
    this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
    this.apiGateway = "http://localhost:8080/api/v1/login"
  }

  public get userValue() {
    return this.userSubject.value;
  }

  login(email: string, password: string) {
    console.log(email);
    console.log(password);
    return this.http.post<User>(`${this.apiGateway}/login`, { email, password })
    .pipe(map(user => {
      console.log(user);
      // store user details and jwt token in local storage to keep user logged in between page refreshes
      localStorage.setItem('user', JSON.stringify(user));
      this.userSubject.next(user);
      return user;
    }));
  }

  logout() {
    // remove user from local storage and set current user to null
    localStorage.removeItem('user');
    this.userSubject.next(null);
    this.router.navigate(['/account/login']);
  }

  register(user: UserRegistration) {
    console.log('User with values: ', user);
    return this.http.post(`${this.apiGateway}/register`, user);
  }

  // getAll() {
  //   return this.http.get<User[]>(`${environment.apiUrl}/login`);
  // }
  //
  // getById(id: string) {
  //   return this.http.get<User>(`${environment.apiUrl}/login/${id}`);
  // }
  //
  // update(id: string, params: any) {
  //   return this.http.put(`${environment.apiUrl}/login/${id}`, params)
  //   .pipe(map(x => {
  //     // update stored user if the logged in user updated their own record
  //     if (id == this.userValue?.id) {
  //       // update local storage
  //       const user = { ...this.userValue, ...params };
  //       localStorage.setItem('user', JSON.stringify(user));
  //
  //       // publish updated user to subscribers
  //       this.userSubject.next(user);
  //     }
  //     return x;
  //   }));
  // }
  //
  // delete(id: string) {
  //   return this.http.delete(`${environment.apiUrl}/users/${id}`)
  //   .pipe(map(x => {
  //     // auto logout if the logged in user deleted their own record
  //     if (id == this.userValue?.id) {
  //       this.logout();
  //     }
  //     return x;
  //   }));
  // }
}
