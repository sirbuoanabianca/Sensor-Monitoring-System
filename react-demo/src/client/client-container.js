import React from 'react';
import {
    Button,
    Card,
    CardHeader,
    Modal,
    ModalBody,
    ModalHeader,
} from 'reactstrap';
import {Launcher} from 'react-chat-window'

import ClientConsumptionChart from "./components/client/client-consumption";
import ClientDevicesTable from './components/device/client-devices-table';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";

import * as API_DEVICES from "./api/device-api"
import SockJsClient from 'react-stomp'
import Alert from 'reactstrap/lib/Alert';

class ClientContainer extends React.Component {

    constructor(props) {
        super(props);
        this.reload = this.reload.bind(this);
        this.deviceBindId = null;
        this.currentClientId = props.currentClientId;
        this.state = {
            isVisibleViewConsumption: false,
            device:null,
            collapseForm: false,
            clientTableData: [],
            deviceTableData: [],
            isLoadedClients: false,
            isLoadedDevices: false,
            isCurrentClient:false,
            alertMessage:null,
            errorStatus: 0,
            messageList: [],
            isOpen:false,
            isFirstOpen:true,
            error: null
        };
    }

    componentDidMount() {
        this.fetchClientDevices();
    }

    fetchClientDevices() {
        return API_DEVICES.getDeviceByUserId(this.currentClientId, (result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    clientDevicesTableData: result,
                    isLoadedDevices: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    reload() {
        this.setState({
            isLoadedDevices: false,
            isLoadedClients: false
        });
        this.fetchClientDevices();
    }

    handleViewConsumptionClick = (device) =>{
        this.setState({ isVisibleViewConsumption: !this.state.isVisibleViewConsumption, device:device });
        
    }

    handleChatClick=()=>{
     
        if(this.state.isFirstOpen)
            {   this.sendMessage({
                action:"requestAdmin",
                sender:localStorage.getItem("userName")
            })
                this.receiveChatMessage("An admin was requested..Please wait")
            }
      
        this.setState({
            isFirstOpen: false,
            isOpen: !this.state.isOpen
        })
    }

    sendMessage = (chatMessage) => {
        this.chatClientRef.sendMessage('/app/chat', JSON.stringify(chatMessage));
      }  


      sendChatMessage(message) {
        this.sendMessage(
            {
                action:"sendMessage",
                message:message.data.text,
                sender:localStorage.getItem("userName"),
                receiver:this.adminName
            }
        )
        this.setState({
          messageList: [...this.state.messageList, message]
        })
      }
     
      receiveChatMessage(text) {
        if (text.length > 0) {
          this.setState({
            messageList: [...this.state.messageList, {
              author: 'them',
              type: 'text',
              data: { text }
            }]
          })
        }
      }



    render() {
        return (
            <div>

                <CardHeader>
                    <strong> Devices Management</strong>
                </CardHeader>

                <Card >
                    <br />
                    <br />

                    {this.state.isLoadedDevices && <ClientDevicesTable tableData={this.state.clientDevicesTableData} handleViewConsumptionClick={this.handleViewConsumptionClick} reload={this.reload} />}
                    {this.state.errorStatus > 0 && <APIResponseErrorMessage
                        errorStatus={this.state.errorStatus}
                        error={this.state.error}
                    />}
                </Card>

                <Modal isOpen={this.state.isVisibleViewConsumption} toggle={this.handleViewConsumptionClick}
                    className={this.props.className} size="lg">
                    <ModalHeader toggle={this.handleViewConsumptionClick}> Select date: </ModalHeader>
                    <ModalBody>
                        <ClientConsumptionChart reloadHandler={this.reload} device={this.state.device}/>
                    </ModalBody>
                </Modal>

                    <div>
                        <SockJsClient url='http://localhost:8080/gs-guide-websocket' topics={['/topic/greetings']}
                        onMessage={(msg) => { 
                        if(this.currentClientId == msg.user_id)
                            {
                                console.log(msg); 
                                this.state.isCurrentClient=true; 
                                this.state.alertMessage=msg.message
                            }
                            else
                                this.state.isCurrentClient=false;
            
                        }}
                        ref={ (client) => { this.clientRef = client }} />
                        {this.state.isCurrentClient && <Alert severity="error">{this.state.alertMessage}</Alert>}

                        
                        <SockJsClient url='http://localhost:8080/chat' topics={['/topic/chat','/topic/message/'+localStorage.getItem("userName")]}
                        onMessage={(msg)=>{
                            console.log(msg);
                            this.adminName=msg.sender;
                            this.receiveChatMessage(msg.message)
                        }}
                        ref={ (chatClient) => { this.chatClientRef = chatClient }} 
                        />

                        <div>
                            <Launcher
                                agentProfile={{
                                teamName: 'Admin',
                                imageUrl: 'https://gravatar.com/avatar/70265334579efe37f4a29ea6c1733791?s=200&d=robohash&r=g'
                                }}
                                onMessageWasSent={this.sendChatMessage.
                                    bind(this)}
                                messageList={this.state.messageList}
                                showEmoji={false}
                                handleClick={this.handleChatClick}
                                isOpen={this.state.isOpen}
                            />
                        </div>

            </div>
                
            </div >
        )

    }
}


export default ClientContainer;
