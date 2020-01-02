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
    getTrain: (id) => {
        return new Promise((resolve, reject) => {
            axios.get(URL + '/train/' + id, { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postNewTimetableItem: (newTimetableItem) => {
        return new Promise((resolve, reject) => {
            axios.post(URL + '/timetable', newTimetableItem, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postChangeConnectionState: (connection) => {
        return new Promise((resolve, reject) => {
            axios.put(URL + '/network/connection', connection, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postDelay: (delay) => {
	console.log(delay);
        return new Promise((resolve, reject) => {
            axios.post(URL + '/delay', delay, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postDamage: (damage) => {
        return new Promise((resolve, reject) => {
            axios.post(URL + '/maintenance/schedule/infrastructure_damage', damage, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postStatus: (statusinfo) => {
        return new Promise((resolve, reject) => {
            axios.post(URL + '/maintenance/schedule/status', statusinfo, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postNewStation: (station) => {
        return new Promise((resolve, reject) => {
            axios.post(URL + '/station', station, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postNewTrain: (train) => {
        return new Promise((resolve, reject) => {
            axios.post(URL + '/train', train, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postNewMaintenanceRequest: (request) => {
        return new Promise((resolve, reject) => {
            axios.post(URL + '/train/' + request.trainId + '/maintenance' , request, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postNewAccidentRequest: (request) => {
        return new Promise((resolve, reject) => {
            axios.post(URL + '/train/' + request.trainId + '/accident' , request, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
    postNewPlatform: (platform) => {
        return new Promise((resolve, reject) => {
            axios.post(URL + '/platform', platform, { headers: { 'Content-Type': 'application/json' } })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
}

export default endpoints;
