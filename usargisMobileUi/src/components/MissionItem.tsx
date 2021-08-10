import * as React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import styles from 'styles/globalStyles';

export interface Mission {
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

export const MissionItem = ({ mission }: { mission: Mission }) => {
    return (
        <View style={style.mainContainer}>
            <View style={style.leftWrapper}>
                <Text style={style.title}>{mission.name}</Text>
                <Text style={style.description} numberOfLines={2}>{mission.description}</Text>
            </View>
            <View style={style.rightWrapper}>
                {/* <Text>{mission.status}</Text> */}
                <View style={style.statusColor}></View>
            </View>
        </View>
    )
}

const style = StyleSheet.create({
    mainContainer: {
        height: 100,
        margin: 10,

        borderColor: '#FA5546',
        borderStyle: 'dashed',
        borderTopWidth: 10,
        borderRightWidth: 0,
        borderBottomWidth: 0,
        borderLeftWidth: 0,
        borderTopEndRadius: 10,
        borderTopStartRadius: 2,
        borderBottomStartRadius: 15,

        shadowColor: "#000",
        shadowOffset: {
            width: 0,
            height: 1,
        },
        shadowOpacity: 0.22,
        shadowRadius: 2.22,
        elevation: 3,

        overflow: 'hidden',
        flexDirection: 'row'
    },
    title: {
        fontWeight:'bold'
    },
    description: {

    },
    leftWrapper: {
        flex: 9,
        paddingTop:5,
        paddingBottom:5,
        paddingLeft:10,
        paddingRight:10,
    },
    rightWrapper: {
        flex: 1
    },
    statusColor: {
        flex: 1,
        marginVertical: 5,
        borderRadius: 100,
    }

});