/**
 * Created by Hrvoje on 31.3.2016.
 * Spinner Component with spinner template which is showing when something is loading
 */

import {Component} from "@angular/core";
@Component({
    selector: 'my-spinner',
    template: `
        <div class="spinnerDivRel">
            <i style="margin-top: 30%;" class="fa fa-spinner fa-pulse fa-5x"></i>
        </div>     
    `
})

export class SpinnerComponent{

}
