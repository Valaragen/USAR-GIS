import React, { useState, useEffect, useRef } from 'react';
import { View, Text, FlatList, ActivityIndicator, StyleSheet } from 'react-native';
import { searchForMissions } from 'api/usargisApi';
import { Mission } from 'utils/types/apiTypes';
import { MissionItem } from 'components/MissionItem';
import { MissionListScreenProps } from 'utils/types/NavigatorTypes';

export default function MissionList({ navigation }: MissionListScreenProps) {
    //const
    const missionFetchPerLoadNb = 10;
    //state
    const [missions, setMissions] = useState<Mission[]>([]);
    const page = useRef(0);
    const [hasMorePages, setHasMorePages] = useState(true);
    const [isLoading, setIsLoading] = useState(false);
    const [isRefreshing, setIsRefreshing] = useState(false);
    const [shouldLoad, setShouldLoad] = useState(true);

    //Load missions on component creation
    useEffect(() => {
        if (shouldLoad) {
            let isSubscribed = true;
            console.log("useEffect is called")
            _loadMissions(isSubscribed);

            return () => {
                isSubscribed = false;
            }
        }
    }, [shouldLoad])

    //Load missions from API
    function _loadMissions(isSubscribed: boolean) {
        setIsLoading(true);
        console.log("searching for page " + page.current);
        searchForMissions(missionFetchPerLoadNb, page.current)
            .then(data => {
                //Do not change state if component is unmounted
                if (isSubscribed) {
                    if (data.length < missionFetchPerLoadNb) setHasMorePages(false);
                    if (page.current === 0) {
                        setMissions(data);
                    } else {
                        setMissions(prevMissions => [...prevMissions, ...data]);
                    }
                    page.current = page.current + 1;
                }
            })
            .catch((error) => {
                if (isSubscribed) {
                    setHasMorePages(false);
                }
                console.log(error);
            })
            .finally(() => {
                if (isSubscribed) {
                    setIsLoading(false);
                    setIsRefreshing(false);
                    setShouldLoad(false);
                }
            });
    }

    function _loadMoreMissions() {
        if (hasMorePages) {
            console.log("Loading more missions");
            setShouldLoad(true);
        }
    }

    function _refresh() {
        console.log("Refreshing...");
        setIsRefreshing(true);
        setHasMorePages(true);
        page.current = 0;
        setMissions([]);
        setShouldLoad(true);
    }

    //Display an ActivityIndicator during application loading
    function _renderFlatListFooter() {
        return (
            <>
                {hasMorePages ?
                    <ActivityIndicator size='large' color='#e22013' animating={isLoading} />
                    :
                    <Text style={style.noMoreResultsText}>Aucun autre résultat</Text>
                }
            </>
        )
    }

    function _displayDetailForFilm(missionId:number) {
        navigation.navigate('MissionDetails', {missionId: missionId});
    }

    return (
        <View style={{ flex: 1 }}>
            <FlatList
                data={missions}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => <MissionItem mission={item} displayDetailForFilm={_displayDetailForFilm}/>}
                onEndReached={() => _loadMoreMissions()}
                onEndReachedThreshold={0.15}
                initialNumToRender={10}
                ListFooterComponent={() => _renderFlatListFooter()}
                onRefresh={() => _refresh()}
                refreshing={isRefreshing}
            />
            {/* <Button title="logout" onPress={() => keycloak?.logout()}></Button> */}
        </View>
    )
}

const style = StyleSheet.create({
    noMoreResultsText: {
        textAlign: 'center',
        fontWeight: 'bold',
        fontSize: 16,
        marginBottom: 10
    },
})