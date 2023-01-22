import React from 'react';
import validate from "../validators/client-validators";
import Button from "react-bootstrap/Button";
import * as API_DEVICES from "../../api/device-api";
import Table from '../../../commons/tables/table'
import APIResponseErrorMessage from "../../../commons/errorhandling/api-response-error-message";
import { Col, Row } from "reactstrap";
import { FormGroup, Input, Label } from 'reactstrap';
import "../../../commons/tables/fields/styles/client-table-style.css";



const filters = [
    {
        accessor: 'username'
    }
]

class BindForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;
        this.data = this.props.tableData;

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.columns = [
            {
                Header: 'Username',
                accessor: 'username'
            },
            {
                Header: 'Role',
                accessor: 'role'
            },
            {
                Header: 'Action',
                accessor: 'action',
                Cell: ({ row }) => (
                    <button onClick={() => { props.handleSubmit(row._original) }}>Bind</button>
                )

            }
        ]


        this.state = {
        }

    };

    toggleForm() {
        this.setState({ collapseForm: !this.state.collapseForm });
    }


    handleChange = event => {

        const name = event.target.name;
        const value = event.target.value;
        const updatedControls = this.state.formControls;

        const updatedFormElement = updatedControls[name];

        updatedFormElement.value = value;
        updatedFormElement.touched = true;
        updatedFormElement.valid = validate(value, updatedFormElement.validationRules);
        updatedControls[name] = updatedFormElement;

        let formIsValid = true;
        for (let updatedFormElementName in updatedControls) {
            formIsValid = updatedControls[updatedFormElementName].valid && formIsValid;
        }

        this.setState({
            formControls: updatedControls,
            formIsValid: formIsValid
        });

    };

    addDevice(device) {
        return API_DEVICES.postDevice(device, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully inserted device with id: " + result);
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    handleSubmit() {
        let device = {
            description: this.state.formControls.description.value,
            max_consumption: this.state.formControls.max_consumption.value,
            address: this.state.formControls.address.value,
        };

        console.log(device);
        this.addDevice(device);
    }


    render() {
        return (
            <Table data={this.data}
                columns={this.columns}
                search={filters}>

            </Table>

        );
    }

}
export default BindForm;
