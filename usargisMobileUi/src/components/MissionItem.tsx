import * as React from 'react';
import { StyleSheet, Text, View } from 'react-native';

interface Mission {
    id: number;
    name: string;
    status: string;
    description?: string;
    startDate?: Date;
    endDate?: Date;
    plannedStartDate?: Date;
    expectedDurationInDays?: number;
    address?: string;
    latitude: number;
    longitude: number;
    creationDate?: Date;
    lastEditionDate?: Date;
    authorUsername: string;
}

const MissionItem = (item: Mission) => {
    return (
        <View>
            <Text>{item.name}</Text>
        </View>
    )
}

const style = StyleSheet.create({
    mainContainer: {
        height:50
    }
});

export default MissionItem;