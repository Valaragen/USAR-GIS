import React, { useState, useEffect, useRef } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { getMissionById } from 'api/usargisApi';
import { Mission } from 'utils/types/apiTypes';
import { MissionDetailsScreenProps } from 'utils/types/NavigatorTypes';

export default function MissionDetails({ route, navigation }: MissionDetailsScreenProps) {
    const [mission, setMission] = useState<Mission>();
    const [isLoading, setIsLoading] = useState(true);

    const { missionId } = route.params;

    useEffect(() => {
        console.log('missionDetail created')
        let isSubscribed = true;
        _loadMissions(isSubscribed);
        return () => {
            console.log('missionDetails detroyed')
            isSubscribed = false;
        }
    }, [])

    function _loadMissions(isSubscribed: boolean) {
        setIsLoading(true);
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
        <View>
            <Text>{mission?.name}</Text>
            <Text>{mission?.description}</Text>
        </View>
    )
}

const style = StyleSheet.create({
    mainContainer: {
        flex: 1,
    }
})