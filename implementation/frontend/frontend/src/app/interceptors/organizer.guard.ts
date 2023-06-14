import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {AccountService} from "../../services/account.service";
import {UserType} from "../../gloabl/userType";


@Injectable({ providedIn: 'root' })
export class OrganizerGuard implements CanActivate {
  constructor(
    private router: Router,
    private accountService: AccountService
  ) {}

  public getuserValue() {
    return this.accountService.userValue;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.getuserValue()?.role === "ORGANIZER") {
      // authorised so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/account/login'], { queryParams: { returnUrl: state.url }});
    return false;
  }
}
