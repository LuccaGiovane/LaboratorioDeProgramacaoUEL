import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import Decks from './Decks';
import DeckDetail from './DeckDetail';
import EditDeck from './EditDeck';

const Stack = createStackNavigator();

const DecksNavScreen = ({ decks, addCard, deleteDeck, editDeck }) => {
  return (
    <Stack.Navigator
      screenOptions={{
        headerStyle: { backgroundColor: '#2E2E2E' },
        headerTintColor: '#fff',
        cardStyle: { backgroundColor: '#2E2E2E' },
      }}
    >
      <Stack.Screen name="DeckList">
        {(props) => (
          <Decks
            {...props}
            decks={decks}
            deleteDeck={deleteDeck}
            editDeck={editDeck}
          />
        )}
      </Stack.Screen>
      <Stack.Screen name="DeckDetail">
        {(props) => <DeckDetail {...props} addCard={addCard} />}
      </Stack.Screen>
      <Stack.Screen name="EditDeck">
        {(props) => <EditDeck {...props} editDeck={editDeck} />}
      </Stack.Screen>
    </Stack.Navigator>
  );
};

export default DecksNavScreen;
