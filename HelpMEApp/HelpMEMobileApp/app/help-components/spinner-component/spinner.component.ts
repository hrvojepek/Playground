/**
 * Created by Hrvoje on 31.3.2016.
 * Spinner Component with spinner template which is showing when something is loading
 */

import {Component} from "angular2/core";
@Component({
    selector: 'my-spinner',
    template: `
        <div style=" background:rgba(255,255,255,0.7); width: 100%; text-align: center; height: 100%; position:fixed; left:0px; top:0px; z-index:99;">
            <i style="margin-top: 3em;" class="fa fa-spinner fa-pulse fa-4x"></i>
        </div>     
    `
})

export class SpinnerComponent{

}