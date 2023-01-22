import React from 'react';
import validate from "../validators/client-validators";
import Button from "react-bootstrap/Button";
import * as API_DEVICES from "../../api/device-api";
import APIResponseErrorMessage from "../../../commons/errorhandling/api-response-error-message";
import { Col, Row } from "reactstrap";
import { FormGroup, Input, Label } from 'reactstrap';

class DeviceForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;


        this.state = {

            errorStatus: 0,
            error: null,

            formIsValid: false,

            formControls: {
                description: {
                    value: '',
                    placeholder: 'What is the description?...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3,
                        isRequired: true
                    }
                },
                max_consumption: {
                    value: '',
                    placeholder: 'Maximum hourly consumption...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        isRequired: true,
                        consumptionValidator: true,
                    }
                },
                address: {
                    value: '',
                    placeholder: 'Address...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        isRequired: true

                    }
                },

            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

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
            <div>

                <FormGroup id='description'>
                    <Label for='descriptionField'> Description: </Label>
                    <Input name='description' id='descriptionField' placeholder={this.state.formControls.description.placeholder}
                        onChange={this.handleChange}
                        defaultValue={this.state.formControls.description.value}
                        touched={this.state.formControls.description.touched ? 1 : 0}
                        required
                    />
                    {this.state.formControls.description.touched &&
                        <div className={"error-message row"}> * Write the device name or details about it </div>}
                </FormGroup>

                <FormGroup id='max_consumption'>
                    <Label for='max_consumptionField'> Maximum hourly consumption: </Label>
                    <Input name='max_consumption' id='max_consumptionField' placeholder={this.state.formControls.max_consumption.placeholder}
                        onChange={this.handleChange}
                        defaultValue={this.state.formControls.max_consumption.value}
                        touched={this.state.formControls.max_consumption.touched ? 1 : 0}
                        valid={this.state.formControls.max_consumption.valid}
                        required
                    />
                    {this.state.formControls.max_consumption.touched && !this.state.formControls.max_consumption.valid &&
                        <div className={"error-message"}> * The consumption must be a number</div>}
                </FormGroup>

                <FormGroup id='address'>
                    <Label for='addressField'> Address: </Label>

                    <Input name='address' id='addressField' placeholder={this.state.formControls.address.placeholder}
                        onChange={this.handleChange}
                        defaultValue={this.state.formControls.address.value}
                        touched={this.state.formControls.address.touched ? 1 : 0}
                        required

                    />
                    {/* {this.state.formControls.role.touched &&
                        <div className={"error-message"}> * The role must be: user or admin</div>} */}
                </FormGroup>


                <Row>
                    <Col sm={{ size: '4', offset: 8 }}>
                        <Button type={"submit"} disabled={!this.state.formIsValid} onClick={this.handleSubmit}>  Add </Button>
                    </Col>
                </Row>

                {
                    this.state.errorStatus > 0 &&
                    <APIResponseErrorMessage errorStatus={this.state.errorStatus} error={this.state.error} />
                }
            </div>
        );
    }
}

export default DeviceForm;
