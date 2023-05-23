import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-users-page',
  templateUrl: './admin-users-page.component.html',
  styleUrls: ['./admin-users-page.component.css']
})
export class AdminUsersPageComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }


  activeCheckbox1 = true;
  activeCheckbox2 = false;

  showEditUserDialog = false;
  showUserDialog = false;
  showUser = false;

  onAddUser() {
    this.showUserDialog = true;
  }

  onConfirmUser() {
    setTimeout(_ => {
      this.showUser = true;
      this.showUserDialog = false;
    }, 400);
  }

  onEditUser() {
    this.showEditUserDialog = true;
    
  }

  onConfirmEditUser() {
    setTimeout(_ => {
      this.activeCheckbox2 = true;
      this.activeCheckbox1 = false;
      this.showEditUserDialog = false;
    }, 200);
  }

  onDeleteUser() {
    let response = confirm('Вы уверены?');
    if (response) {
      setTimeout(_ => this.showUser = false, 400);
    }
  }
}
