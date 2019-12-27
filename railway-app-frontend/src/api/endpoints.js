import axios from 'axios';
import {URL} from './constants';

const endpoints = {
    getStations: () => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/station', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    getTrains: () => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/train', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    getStaff: () => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/staff', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    getTimetable: () => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/timetable/all', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    getRoutes: () => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/network/route/all', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    getStationsOnRoute: (routeId) => {
        return new Promise((resolve, reject) => {
            axios.get(URL + `/network/route/${routeId}/stations`, { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    getConnectionsOnRoute: (routeId) => {
        return new Promise((resolve, reject) => {
            axios.get(URL + `/network/route/${routeId}/connections`, { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postNewTimetableItem: (newTimetableItem) => {
        return new Promise((resolve, reject) => {
            axios.post(URL + '/timetable', newTimetableItem, { headers: { 'Content-Type': 'application/json' } })
                .then(result => {
                    if(typeof result.data === "string") {
                        reject(result.data);
                    } else {
                        resolve(result);
                    }
                })
                .catch(error => { reject(error); });
        });
    },
    postCreateConnection: (connection) => {
        return new Promise((resolve, reject) => {
            const formattedConnection = {
                stationX: {
                    stationId: connection.stationXId,
                },
                stationY: {
                    stationId: connection.stationYId,
                },
                distance: connection.distance,
                maxSpeed: connection.maxSpeed,
                active: connection.active,
            };

            axios.post(URL + '/network/connection', formattedConnection, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result.data); })
                .catch(error => { reject(error); });
        });
    },
    putChangeConnectionState: (connection) => {
        return new Promise((resolve, reject) => {
            axios.put(URL + '/network/connection', connection, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
}

export default endpoints;
