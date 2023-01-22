
import React from 'react';
import "../commons/tables/fields/styles/login-style.css"
import * as API_LOGIN from "./api/login-api"
import {
    Routes,
    Route,
    Link,
    useHistory,
    Redirect
} from 'react-router-dom';

import {
    Button
} from 'reactstrap';



class LoginContainer extends React.Component {

    constructor(props) {
        super(props);
        this.reset();
        this.history = props.history;
        localStorage.setItem("isLoggedIn", 'false');

        this.state = {
            inputValueUser: '',
            inputValuePass: '',
        }

    }

    reset() {
        this.state = {
            inputValueUser: '',
            inputValuePass: '',

        };
        localStorage.setItem("isLoggedIn", 'false');

    }

    updateInputValueUser(evt) {
        const val = evt.target.value;
        this.setState({
            inputValueName: val
        });
    }
    updateInputValuePass(evt) {
        const val = evt.target.value;

        this.setState({
            inputValuePass: val
        });
    }


    handleLoginClick = () => {
        console.log("name=", this.state.inputValueName);
        console.log("pass=", this.state.inputValuePass);

        return API_LOGIN.loginUser({ username: this.state.inputValueName, password: this.state.inputValuePass }, (result, status, err) => {


            if (result !== null && status === 200) {
                localStorage.setItem("isLoggedIn", 'true');
                localStorage.setItem("userName",result.username);


                if (result.role === "admin") {
                    localStorage.setItem("isAdmin", 'true');
                    this.history.push('/admin');
                }
                else {
                    localStorage.setItem("clientId", result.id);
                    localStorage.setItem("isAdmin", 'false');
                    this.history.push('/client');
                }

                this.setState({});
            } else {
                localStorage.setItem("isLoggedIn", 'false');
                this.renderErrorMessage();
                this.setState(({
                }));
            }
        });
    }

    renderErrorMessage = () =>
    (
        <div className="error">Invalid username or password</div>
    );


    render() {
        return (
            <div id="loginform">
                <FormHeader title="Login" />
                <div>
                    <FormInput description="Username" placeholder="Enter your username" type="text" value={this.state.inputValueName} onChange={(evt) => { this.updateInputValueUser(evt) }} />
                    <FormInput description="Password" placeholder="Enter your password" type="password" value={this.state.inputValuePass} onChange={(evt) => { this.updateInputValuePass(evt) }} />
                    <div id="button" >
                        <Button id="login-button" onClick={this.handleLoginClick}>Log in</Button>
                    </div>

                </div>
            </div>

        )
    }
}

const FormHeader = props => (
    <h2 id="headerTitle">{props.title}</h2>
);

const FormInput = props => (
    <div class="row-div">
        <label class="label">{props.description}</label>
        <input type={props.type} placeholder={props.placeholder} value={props.value} onChange={props.onChange} class="row-input" />
    </div>
);
export default function (props) {
    const history = useHistory();

    return <LoginContainer {...props} history={history} />;
}
