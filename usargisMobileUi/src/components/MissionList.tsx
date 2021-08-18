import React, { useState, useReducer, useEffect, useRef } from 'react';
import { View, Text, FlatList, ActivityIndicator, StyleSheet } from 'react-native';
import { searchForMissions } from 'api/usargisApi';
import { Mission } from 'utils/types/apiTypes';
import { MissionItem } from 'components/MissionItem';
import { MissionListScreenProps } from 'utils/types/navigatorTypes';
import HttpError from 'api/HttpError';


//const
const missionFetchPerLoadNb = 10;

type State = {
    missions: Mission[],
    hasMorePages: boolean,
    isLoading: boolean,
    shouldLoad: boolean,
}

type Action = { type: 'REFRESH' } 
| { type: 'NO_MORE_PAGES'}
| { type: 'MISSIONS_FOUND', payload: Mission[] }
| { type: 'LOADING_END' }
| { type: 'SHOULD_LOAD'}

const initialState: State = {
    missions: [],
    hasMorePages: true,
    isLoading: false,
    shouldLoad: true,
}

function reducer(state: State, action: Action): State {
    switch (action.type) {
        case 'REFRESH':
            return {
                ...state,
                missions: [],
                hasMorePages: true,
                shouldLoad: true,
                isLoading: true,
            };
        case 'NO_MORE_PAGES':
            return {
                ...state,
                hasMorePages: false,
                isLoading: false,
                shouldLoad: false,
            };
        case 'MISSIONS_FOUND':
            return {
                ...state,
                missions: [...state.missions, ...action.payload],
                hasMorePages: (action.payload.length < missionFetchPerLoadNb) ? false : true,
                isLoading: false,
                shouldLoad: false,
            };
        case 'SHOULD_LOAD':
            return {
                ...state,
                shouldLoad: true,
                isLoading: true,
            };
        case 'LOADING_END':
            return {
                ...state,
                isLoading: false,
                shouldLoad: false
            }
        default:
            throw new Error();
    }
}

export default function MissionList({ navigation }: MissionListScreenProps) {
    //state
    const page = useRef(0);
    const isRefreshing = useRef(false);

    const [state, dispatch] = useReducer(reducer, initialState);


    //Load missions on component creation
    useEffect(() => {
        if (state.shouldLoad) {
            let isSubscribed = true;
            console.log("useEffect is called")
            _loadMissions(isSubscribed);

            return () => {
                isSubscribed = false;
            }
        }
    }, [state.shouldLoad])

    //Load missions from API
    function _loadMissions(isSubscribed: boolean) {
        console.log("searching for page " + page.current);
        searchForMissions(missionFetchPerLoadNb, page.current)
            .then(data => {
                //Do not change state if component is unmounted
                if (isSubscribed) {
                    dispatch({type: 'MISSIONS_FOUND', payload: data})
                    page.current = page.current + 1;
                }
            })
            .catch((error) => {
                if (isSubscribed) {
                    if (error instanceof HttpError) {
                        if (error.statusCode === 404) {
                            dispatch({ type: 'NO_MORE_PAGES' });
                        } else {
                            dispatch({ type: 'LOADING_END'})
                        }
                    } else {
                        throw new Error("Unhandled error");
                    }
                }
            })
            isRefreshing.current = false;
    }

    function _loadMoreMissions() {
        if (state.hasMorePages) {
            console.log("Loading more missions");
            dispatch({ type: 'SHOULD_LOAD' });
        }
    }

    function _refresh() {
        isRefreshing.current = true;
        page.current = 0;
        console.log("Refreshing...");
        dispatch({ type: "REFRESH" });
    }

    //Display an ActivityIndicator during application loading
    function _renderFlatListFooter() {
        return (
            <>
                {state.hasMorePages ?
                    <ActivityIndicator size='large' color='#e22013' animating={state.isLoading} />
                    :
                    <Text style={style.noMoreResultsText}>Aucun autre résultat</Text>
                }
            </>
        )
    }

    function _displayDetailForFilm(missionId: number) {
        navigation.navigate('MissionDetails', { missionId: missionId });
    }

    return (
        <View style={{ flex: 1 }}>
            <FlatList
                data={state.missions}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => <MissionItem mission={item} displayDetailForFilm={_displayDetailForFilm} />}
                onEndReached={() => _loadMoreMissions()}
                onEndReachedThreshold={0.15}
                initialNumToRender={10}
                ListFooterComponent={() => _renderFlatListFooter()}
                onRefresh={() => _refresh()}
                refreshing={isRefreshing.current}
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