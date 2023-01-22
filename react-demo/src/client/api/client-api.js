import { HOST } from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    client: '/client/',
};

function getClients(callback) {
    let request = new Request(HOST.backend_api + endpoint.client, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getClientById(params, callback) {
    let request = new Request(HOST.backend_api + endpoint.client + params.id, {
        method: 'GET'
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postClient(user, callback) {
    let request = new Request(HOST.backend_api + endpoint.client, {
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

function updateClient(user, callback) {
    let request = new Request(HOST.backend_api + endpoint.client, {
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

function deleteClient(id, callback) {
    let request = new Request(HOST.backend_api + endpoint.client + id, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}



export {
    getClients,
    getClientById,
    postClient,
    updateClient as updateDevice,
    deleteClient,
};
