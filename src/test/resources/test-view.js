import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';

export class TestView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-text-field id="a-text-field" placeholder="Hello" style="width: 100%;"></vaadin-text-field>
<span id="a-span">World</span>
<vaadin-grid id="a-grid"></vaadin-grid>
`;
    }

    static get is() {
        return 'test-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(TestView.is, TestView);
