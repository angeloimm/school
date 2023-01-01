import { Component, OnInit } from '@angular/core';
import { LayoutService } from '../service/layout.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit{
  constructor(public layoutService:LayoutService){}
  ngOnInit(): void {
  }
}
