import React, { useState, useEffect } from 'react';
import { View, Text, Pressable, FlatList, Button, ActivityIndicator } from 'react-native';
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

function AppNavigation() {
    //Keycloak
    const { keycloak } = useKeycloak();
    const parsedToken: ParsedToken | undefined = keycloak?.tokenParsed;
    //const
    const missionFetchPerLoadNb = 10;
    //state
    const [missions, setMissions] = useState<Mission[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [page, setPage] = useState(0);
    const [hasMorePages, setHasMorePages] = useState(true);

    //Load missions on component creation
    useEffect(() => {
        let isSubscribed = true;
        _loadMissions(page, isSubscribed);
        setPage(page + 1)
        return () => {
            isSubscribed = false;
            setIsLoading(false);
        }
    }, [])

    //Load missions from API
    function _loadMissions(pageNo:number, isSubscribed:boolean = true) {
        setIsLoading(true);
        searchForMissions(missionFetchPerLoadNb, pageNo)
        .then(data => {
                //Do not change state if component is unmounted
                if(isSubscribed) {
                    if (page === 0) {
                        setMissions(data);
                    } else {
                        setMissions([...missions, ...data]);
                    }
                }
            })
            .catch((error) => {
                setHasMorePages(false);
            })
            .finally(() => {
                if(isSubscribed) {
                    setIsLoading(false);
                }
            });

    }

    function _loadMoreMissions() {
        setPage(page + 1);
        _loadMissions(page);
        setIsLoading(true);
        console.log("loadmore")
    }

    //Display an ActivityIndicator during application loading
    function _displayLoading() {
            return (
                <>
                { hasMorePages ?
                    <ActivityIndicator size='large' color='#e22013' animating={isLoading}/>
                    :
                    <Text>Fin de la recherche</Text>
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
                onEndReached={() => {
                    if (hasMorePages) {
                        _loadMoreMissions()}
                    }
                }
                onEndReachedThreshold={0.15}
                initialNumToRender={10}
                ListFooterComponent={() => _renderFlatListFooter()}
            />
            {/* <Button title="logout" onPress={() => keycloak?.logout()}></Button> */}
        </View>
    )
}

export default AppNavigation;