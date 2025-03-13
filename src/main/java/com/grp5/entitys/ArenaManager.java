package com.grp5.entitys;

import DAOklasser.AddressDAO;
import DAOklasser.ArenaDAO;

public class ArenaManager {

    public void registerArena(String arenaName, String street, String houseNumber, String postalCode, String city, boolean isIndoor) {
        try {
            AddressDAO addressDAO = new AddressDAO();
            ArenaDAO arenaDAO = new ArenaDAO();

            // Kontrollera om arenan redan finns
            Arena existingArena = arenaDAO.getArenaByArenaName(arenaName);
            if (existingArena != null) {
                System.out.println("Arenan med detta namn finns redan.");
                return; // Avsluta om den redan finns
            }

            // Kontrollera om adressen redan finns
            Addresses address = addressDAO.findAddress(street, houseNumber, postalCode, city);
            if (address == null) {
                // Om adressen inte finns, skapa och spara den
                address = new Addresses();
                address.setStreet(street);
                address.setHouse_number(houseNumber);
                address.setPostal_code(postalCode);
                address.setCity(city);

                addressDAO.saveAddress(address);
            }

            // Skapa och spara arena
            Arena newArena = new Arena(arenaName, address, isIndoor);
            arenaDAO.saveArena(newArena);

            System.out.println("Arenan har lagts till i databasen!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fel vid registrering av arena.");
        }
    }
}
