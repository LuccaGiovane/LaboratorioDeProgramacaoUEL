import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Modal, TextInput, FlatList } from 'react-native';

const DeckDetail = ({ route }) => {
  const { deck } = route.params;
  const [testHandModalVisible, setTestHandModalVisible] = useState(false);
  const [addCardModalVisible, setAddCardModalVisible] = useState(false);
  const [editCardModalVisible, setEditCardModalVisible] = useState(false);
  const [hand, setHand] = useState([]);
  const [newCardName, setNewCardName] = useState('');
  const [newCardQuantity, setNewCardQuantity] = useState('');
  const [editCard, setEditCard] = useState(null);

  const drawHand = () => {
    let expandedDeck = [];
    deck.cards.forEach(card => {
      for (let i = 0; i < card.quantity; i++) {
        expandedDeck.push(card.name);
      }
    });

    for (let i = expandedDeck.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [expandedDeck[i], expandedDeck[j]] = [expandedDeck[j], expandedDeck[i]];
    }

    const drawnHand = expandedDeck.slice(0, 7);
    setHand(drawnHand);
    setTestHandModalVisible(true);
  };

  const addCardToDeck = () => {
    if (newCardName && newCardQuantity) {
      const newCard = {
        name: newCardName,
        quantity: parseInt(newCardQuantity),
      };
      deck.cards.push(newCard);
      setAddCardModalVisible(false);
      setNewCardName('');
      setNewCardQuantity('');
    }
  };

  const removeCardFromDeck = (index) => {
    deck.cards.splice(index, 1);
    setEditCardModalVisible(false);
  };

  const openEditCardModal = (card, index) => {
    setEditCard({ ...card, index });
    setNewCardName(card.name);
    setNewCardQuantity(card.quantity.toString());
    setEditCardModalVisible(true);
  };

  const saveEditCard = () => {
    const updatedCard = {
      name: newCardName,
      quantity: parseInt(newCardQuantity),
    };
    deck.cards[editCard.index] = updatedCard;
    setEditCardModalVisible(false);
    setEditCard(null);
    setNewCardName('');
    setNewCardQuantity('');
  };

  const renderCardItem = ({ item, index }) => (
    <View style={styles.cardContainer}>
      <Text style={styles.cardText}>{item.quantity} {item.name}</Text>
      <View style={styles.cardButtons}>
        <TouchableOpacity style={styles.editButton} onPress={() => openEditCardModal(item, index)}>
          <Text style={styles.buttonText}>Edit</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.deleteButton} onPress={() => removeCardFromDeck(index)}>
          <Text style={styles.buttonText}>Delete</Text>
        </TouchableOpacity>
      </View>
    </View>
  );

  return (
    <View style={styles.container}>
      {deck.cards.length === 0 ? (
        <Text style={styles.emptyText}>Deck is empty</Text>
      ) : (
        <FlatList
          data={deck.cards}
          renderItem={renderCardItem}
          keyExtractor={(item, index) => index.toString()}
        />
      )}
      <TouchableOpacity style={styles.button} onPress={() => setAddCardModalVisible(true)}>
        <Text style={styles.buttonText}>Add Card</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button} onPress={drawHand}>
        <Text style={styles.buttonText}>Test Hand</Text>
      </TouchableOpacity>

      {/* Add Card Modal */}
      <Modal
        animationType="slide"
        transparent={true}
        visible={addCardModalVisible}
        onRequestClose={() => setAddCardModalVisible(!addCardModalVisible)}
      >
        <View style={styles.modalView}>
          <Text style={styles.modalTitle}>Add Card</Text>
          <TextInput
            style={styles.input}
            placeholder="Card Name"
            placeholderTextColor="#fff"
            value={newCardName}
            onChangeText={setNewCardName}
          />
          <TextInput
            style={styles.input}
            placeholder="Quantity"
            placeholderTextColor="#fff"
            value={newCardQuantity}
            onChangeText={setNewCardQuantity}
            keyboardType="numeric"
          />
          <TouchableOpacity style={styles.button} onPress={addCardToDeck}>
            <Text style={styles.buttonText}>Add</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => setAddCardModalVisible(false)}>
            <Text style={styles.buttonText}>Close</Text>
          </TouchableOpacity>
        </View>
      </Modal>

      {/* Edit Card Modal */}
      <Modal
        animationType="slide"
        transparent={true}
        visible={editCardModalVisible}
        onRequestClose={() => setEditCardModalVisible(!editCardModalVisible)}
      >
        <View style={styles.modalView}>
          <Text style={styles.modalTitle}>Edit Card</Text>
          <TextInput
            style={styles.input}
            placeholder="Card Name"
            placeholderTextColor="#fff"
            value={newCardName}
            onChangeText={setNewCardName}
          />
          <TextInput
            style={styles.input}
            placeholder="Quantity"
            placeholderTextColor="#fff"
            value={newCardQuantity}
            onChangeText={setNewCardQuantity}
            keyboardType="numeric"
          />
          <TouchableOpacity style={styles.button} onPress={saveEditCard}>
            <Text style={styles.buttonText}>Save</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button} onPress={() => setEditCardModalVisible(false)}>
            <Text style={styles.buttonText}>Close</Text>
          </TouchableOpacity>
        </View>
      </Modal>

      {/* Test Hand Modal */}
      <Modal
        animationType="slide"
        transparent={true}
        visible={testHandModalVisible}
        onRequestClose={() => setTestHandModalVisible(!testHandModalVisible)}
      >
        <View style={styles.modalView}>
          <Text style={styles.modalTitle}>Test Hand</Text>
          {hand.length > 0 ? (
            hand.map((card, index) => (
              <Text key={index} style={styles.cardText}>{card}</Text>
            ))
          ) : (
            <Text style={styles.emptyText}>Deck is empty</Text>
          )}
          <TouchableOpacity style={styles.button} onPress={() => setTestHandModalVisible(false)}>
            <Text style={styles.buttonText}>Close</Text>
          </TouchableOpacity>
        </View>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#2E2E2E',
    padding: 16,
  },
  emptyText: {
    color: 'purple',
    fontWeight: 'bold',
    textAlign: 'center',
    fontSize: 18,
  },
  cardContainer: {
    backgroundColor: '#474A51',
    borderRadius: 10,
    padding: 10,
    marginVertical: 5,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  cardText: {
    color: '#fff',
    fontWeight: 'bold',
  },
  cardButtons: {
    flexDirection: 'row',
  },
  button: {
    backgroundColor: 'purple',
    padding: 10,
    marginVertical: 5,
    borderRadius: 5,
    alignItems: 'center',
  },
  buttonText: {
    color: '#000',
    fontWeight: 'bold',
  },
  editButton: {
    backgroundColor: '#A9A9A9',
    padding: 5,
    borderRadius: 5,
    marginHorizontal: 5,
  },
  deleteButton: {
    backgroundColor: '#A9A9A9',
    padding: 5,
    borderRadius: 5,
  },
  modalView: {
    margin: 20,
    backgroundColor: '#2E2E2E',
    borderRadius: 20,
    padding: 35,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  modalTitle: {
    color: 'purple',
    fontWeight: 'bold',
    fontSize: 18,
    marginBottom: 15,
    textAlign: 'center',
  },
  input: {
    height: 40,
    borderColor: '#474A51',
    borderWidth: 1,
    marginBottom: 20,
    paddingHorizontal: 10,
    color: '#fff',
    backgroundColor: '#474A51',
    width: '80%',
  },
});

export default DeckDetail;