import React, { useState, useEffect } from 'react';
import { ScrollView, Text, StyleSheet, ActivityIndicator } from 'react-native';
import moment from 'moment';

import { getMissionById } from 'api/usargisApi';
import { Mission } from 'utils/types/apiTypes';
import { MissionDetailsScreenProps } from 'utils/types/navigatorTypes';

export default function MissionDetails({ route, navigation }: MissionDetailsScreenProps) {
    const [mission, setMission] = useState<Mission>();
    const [isLoading, setIsLoading] = useState(true);

    const { missionId } = route.params;

    useEffect(() => {
        console.log('missionDetails created')
        let isSubscribed = true;
        _loadMissions(isSubscribed);
        return () => {
            console.log('missionDetails detroyed')
            isSubscribed = false;
        }
    }, [])

    function _loadMissions(isSubscribed: boolean) {
        console.log('Looking for mission with id', missionId)
        getMissionById(missionId)
            .then(data => {
                //Do not change state if component is unmounted
                if (isSubscribed) {
                    setMission(data);
                }
            })
            .catch((error) => {
                console.log(error);
            })
            .finally(() => {
                if (isSubscribed) {
                    setIsLoading(false);
                }
            })
    }


    return (
        <ScrollView style={style.mainContainer}>
            {isLoading ?
                <ActivityIndicator size='large' color='#e22013' style={style.loading} /> 
                :
                <>
                    <Text>nom: {mission?.name}</Text>
                    <Text>description: {mission?.description}</Text>
                    <Text>status: {mission?.status}</Text>
                    <Text>date de création: {moment(mission?.creationDate).format('DD/MM/YY')}</Text>
                    <Text>Date de début prévue: {moment(mission?.plannedStartDate).format('DD/MM/YY')}</Text>
                    <Text>Durée de la mission: {mission?.expectedDurationInDays} jours</Text>
                    <Text>Édité le: {moment(mission?.lastEditionDate).format('DD/MM/YY')}</Text>
                    <Text>Commence le: {moment(mission?.startDate).format('DD/MM/YY')}</Text>
                </>
            }
        </ScrollView>
    )
}

const style = StyleSheet.create({
    mainContainer: {
        flex: 1,
    },
    loading: {
        alignContent: 'center',
        textAlign:'center',
    }
})