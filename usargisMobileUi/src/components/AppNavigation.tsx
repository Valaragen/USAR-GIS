import React, { useState, useEffect, useRef } from 'react';
import { View, Text, Pressable, FlatList, Button, ActivityIndicator, StyleSheet } from 'react-native';
import { useKeycloak } from '@react-keycloak/native';
import { KeycloakTokenParsed } from '@react-keycloak/keycloak-ts';
import { searchForMissions } from 'api/usargisApi';
import { MissionItem, Mission } from 'components/MissionItem';

type ParsedToken = KeycloakTokenParsed & {
    email?: string;
    preferred_username?: string;
    given_name?: string;
    family_name?: string;
}

export default function AppNavigation() {
    //Keycloak
    const { keycloak } = useKeycloak();
    const parsedToken: ParsedToken | undefined = keycloak?.tokenParsed;
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
    function _loadMissions(isSubscribed:boolean) {
        setIsLoading(true);
        console.log("searching for page " + page.current);
        searchForMissions(missionFetchPerLoadNb, page.current)
        .then(data => {
            //Do not change state if component is unmounted
            if(isSubscribed) {
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
            if(isSubscribed) {
                setHasMorePages(false);
            }
            console.log(error);
        })
        .finally(() => {
            if(isSubscribed) {
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
    function _displayLoading() {
            return (
                <>
                { hasMorePages ?
                    <ActivityIndicator size='large' color='#e22013' animating={isLoading}/>
                    :
                    <Text style={style.noMoreResultsText}>Aucun autre résultat</Text>
                }
                </>
            )
    }

    function _renderFlatListFooter() {
        return _displayLoading();
    }

    return (
        <View style={{flex:1}}>
            <FlatList
                data={missions}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => <MissionItem mission={item}/>}
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