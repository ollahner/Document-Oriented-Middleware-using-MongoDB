# Abgabe Cheatsheet - MongoDB Warehouse

## 1. Infrastruktur & Start

### MongoDB Container
```powershell
# Starten (falls neu)
docker run -d -p 27017:27017 --name mongo mongo

# Starten (falls bereits vorhanden)
docker start mongo
```

### Applikation starten
```powershell
.\gradlew.bat bootRun
```
*Hinweis: Die App löscht beim Start alle alten Daten (`repository.deleteAll()`) und lädt automatisch **5 Lager** mit insgesamt **300 Produkten** in **6 Kategorien** (Vertiefungs-Anforderung).*

---

## 2. MongoDB Shell (mongosh)

### Shell öffnen & Datenbank wählen
```powershell
docker exec -it mongo mongosh
use warehouse
```

### Basis-Abfragen (CRUD)
```javascript
// Alle Dokumente in der Collection "warehouses" anzeigen
db.warehouses.find().pretty()

// Anzahl der Dokumente prüfen (sollte jetzt 5 sein)
db.warehouses.countDocuments()

// Alle Produkte in Lager 1 zählen
db.warehouses.aggregate([
  { $match: { warehouseId: 1 } },
  { $project: { count: { $size: "$productData" } } }
])

// Suchen nach einem bestimmten Lager (ID 1)
db.warehouses.find({ warehouseId: 1 })

// Neues Warehouse hinzufügen
db.warehouses.insertOne({
  warehouseId: 3,
  warehouseName: "Lager 3",
  warehousePostalCode: "5020",
  warehouseCity: "Salzburg",
  warehouseCountry: "Austria",
  timestamp: new Date(),
  productData: []
})

// Lager 2 löschen
db.warehouses.deleteOne({ warehouseId: 2 })
```

### Produkt-Operationen (Verschachtelte Dokumente)
```javascript
// Produkt zu einem bestehenden Warehouse hinzufügen (z.B. zu ID 1)
db.warehouses.updateOne(
  { warehouseId: 1 },
  { $push: { 
      productData: { 
        productId: 4, 
        productName: "Produkt 4", 
        productCategory: "Kat 2", 
        productQuantity: 50 
      } 
    } 
  }
)

// Alle Lager finden, die "Produkt 1" führen
db.warehouses.find({ "productData.productName": "Produkt 1" })

// Nur die Produktliste von Lager 1 anzeigen (ohne _id)
db.warehouses.find({ warehouseId: 1 }, { productData: 1, _id: 0 })
```

### Aggregationen (Berichtswesen - Vertiefung)
Hier sind drei praxisrelevante Fragestellungen für die Zentrale:

<font size="5">**Frage 1: Globaler Lagerbestand eines spezifischen Produkts**</font>
*Szenario: Wie viele Einheiten von "Produkt 1" haben wir insgesamt über alle Standorte hinweg?*
```javascript
db.warehouses.aggregate([
  { $unwind: "$productData" },
  { $match: { "productData.productName": "Produkt 1" } },
  { $group: { _id: "$productData.productName", gesamtBestand: { $sum: "$productData.productQuantity" } } }
])
```

<font size="5">**Frage 2: Warnliste für niedrige Bestände (Nachbestellung)**</font>
*Szenario: Welche Produkte haben an welchen Standorten weniger als 50 Stück auf Lager?*
```javascript
db.warehouses.aggregate([
  { $unwind: "$productData" },
  { $match: { "productData.productQuantity": { $lt: 50 } } },
  { $project: { _id: 0, warehouseName: 1, "productData.productName": 1, "productData.productQuantity": 1 } }
])
```

<font size="5">**Frage 3: Bestands-Analyse pro Kategorie**</font>
*Szenario: Welchen gesamten Warenbestand haben wir in den jeweiligen Kategorien (z.B. Reinigung, Snacks)?*
```javascript
db.warehouses.aggregate([
  { $unwind: "$productData" },
  { $group: { _id: "$productData.productCategory", gesamtMenge: { $sum: "$productData.productQuantity" } } },
  { $sort: { gesamtMenge: -1 } }
])
```

---

## 3. Datenbank aufräumen
```javascript
// Die gesamte Collection "warehouses" leeren
db.warehouses.deleteMany({})

// Oder die ganze Collection löschen
db.warehouses.drop()
```
