import axios from 'axios';
import {URL} from './constants';

const endpoints = {
    getStations: function(){
        return new Promise((resolve, reject) => {
            axios.get(URL + '/station', { responseType: 'json' })
                .then(result => { resolve(result); })
                .catch(error => { reject(error); });
        });
    },
}

export default endpoints;
