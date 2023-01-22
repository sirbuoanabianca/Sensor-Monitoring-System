import { HOST } from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    device: '/device/',
    bind: '/device/bind',
    consumptionRecords:'/consumptionRecord/'
};
function getDevices(callback) {
    let request = new Request(HOST.backend_api + endpoint.device, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getDeviceByUserId(id, callback) {
    let request = new Request(HOST.backend_api + endpoint.device + 'client/' + id, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postDevice(user, callback) {
    let request = new Request(HOST.backend_api + endpoint.device, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function updateDevice(user, callback) {
    let request = new Request(HOST.backend_api + endpoint.device, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function deleteDevice(id, callback) {
    let request = new Request(HOST.backend_api + endpoint.device + id, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function bindDeviceToUser(deviceId, userId, callback) {
    let request = new Request(HOST.backend_api + endpoint.bind, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id: deviceId, userId })
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function getDeviceRecordsByDay(date,id, callback) {
    let request = new Request(HOST.backend_api + endpoint.consumptionRecords + 'fromDay/'+'?id=' + id + '&' + 'date='+date, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

export {
    getDevices,
    postDevice,
    updateDevice,
    deleteDevice,
    bindDeviceToUser,
    getDeviceByUserId,
    getDeviceRecordsByDay,
};
