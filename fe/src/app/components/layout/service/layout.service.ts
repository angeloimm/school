import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LayoutService {
    private topBarInfoPanelEnabled:boolean = true;
    constructor(){}
    public hideTopBarInfoPanel(){
        this.topBarInfoPanelEnabled = false;
    }
    public showTopBarInfoPanel(){
        this.topBarInfoPanelEnabled = true;
    }
    public isPanelVisible(){
        return this.topBarInfoPanelEnabled;
    }
}
