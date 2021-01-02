const initialState = { testArray: [] };

function toggleFavorite(state = initialState, action) {
    let nextState;
    switch(action.type) {
        case 'TOGGLE_FAVORITE':
            nextState = {
                ...state,
                testArray: [pepe]
            }
            return nextState;
        default:
            return state;
    }
}

export default toggleFavorite;