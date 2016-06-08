/**
 * Created by Hrvoje on 28.05.2016..
 */

import {Component, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'add-feedback',
  providers: [],
  pipes: [],
  directives: [],
  template: `
    <div class="popupContainer">
        <div class="addFeedback">
          <i class="fa fa-times fa-3x" style="float: right; margin-top: -1em; cursor: pointer;" (click)="closePopup()"></i>
          <br>
          <h2>Add Your Comment About This Website</h2>
          <br><br>
          <textarea placeholder="Write Your Comment Here" cols="60" rows="15" #commenttxt></textarea>
          <br><br>
          <a class="work" style="cursor: pointer;" (click)="comment(commenttxt.value)">Comment</a>
          
        </div>
    </div>
    
  `,
})

export class AddFeedbackPopupComponent {

  @Output() c: EventEmitter<any> = new EventEmitter<any>();

  comment(commenttxt: string): void{
    this.c.emit(commenttxt);
  }

  closePopup(): void{
    this.c.emit('');
  }

}
