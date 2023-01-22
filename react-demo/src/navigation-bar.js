import React from 'react'
import logo from './commons/images/energy_icon.png';

import {

    Nav,
    Navbar,
    NavbarBrand,

} from 'reactstrap';

const textStyle = {
    color: 'white',
    textDecoration: 'none'
};

const NavigationBar = (getIsLoggedIn) => (
    <div>
        <Navbar color="dark" light expand="md">
            <NavbarBrand href="/">
                <img src={logo} width={"35"} height={"35"} />


            </NavbarBrand>
            <Nav className="mr-auto" navbar>
                <span className="navItem">Smart energy metering </span>
            </Nav>
        </Navbar>
    </div>
);

export default NavigationBar
