'use strict';

import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';
import ReactDOM from "react-dom";
// import App from "./component/App";

var path = require('path');

class Button extends Component {
    render() {
        return <button color='blue' onClick='console.log( "ouch!")'>Button</button>;
    }
}


ReactDOM.render(<App />, document.getElementById("root"));

export default Button;