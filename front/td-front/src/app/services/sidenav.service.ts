import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SidenavService {

    private sidenavOpen = new Subject<void>();
    private sidenavClose = new Subject<void>();
    private sidenavToggle = new Subject<void>();

    sidenavOpen$ = this.sidenavOpen.asObservable();
    sidenavClose$ = this.sidenavClose.asObservable();
    sidenavToggle$ = this.sidenavToggle.asObservable();

    constructor() { }

    open() {
        this.sidenavOpen.next();
    }

    close() {
        this.sidenavClose.next();
    }

    toggle() {
        this.sidenavToggle.next();
    }
}
