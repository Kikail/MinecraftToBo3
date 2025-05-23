# Block2Prefab

**Convert Minecraft schematic builds into Call of Duty: Black Ops 3 `.map` prefabs.**

---

## 🙏 Credits

- **GoldenDelicious** – for [Lite2Edit](https://github.com/GoldenDelicios/Lite2Edit), used to parse `.litematic` files
- **SandroHc** – for [schematic4j](https://github.com/SandroHc/schematic4j), which helped with Minecraft block extraction

---

## 🧩 What is this?

**Block2Prefab** is a Java tool that transforms Minecraft schematic files—such as `.litematic`, `.schem`, and `.schematic`—into prefabs compatible with the **Call of Duty: Black Ops 3 Mod Tools**.

This allows you to bring your Minecraft structures directly into your custom BO3 zombie or multiplayer maps using Radiant.

---

## 🔧 Features

- 📦 Converts `.litematic`, `.schem`, and `.schematic` formats
- 🧱 Preserves block positions, sizes, and orientation
- 🎮 Outputs `.map` prefab files ready for BO3 Radiant
- ⚙️ Fully customizable block-to-prefab/material/model mappings
- 💡 Easily extensible by the user

---

## ⚙️ Prerequisites

Before using Block2Prefab, make sure you have the following installed:

- ✅ [Java 22.0](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html) or later
- ✅ [Minecraft Mod Tools Pack by Sphynx](https://www.t7wiki.com/Minecraft-Mod-Tools-Pack) – Required to support Minecraft-style blocks and assets in BO3

---

## 📥 Installation

Drag and drop the map_source folder in the Bo3 root folder

---

## 🔁 Block Mapping System (Customizable)

Block2Prefab uses a **user-editable `.txt` file** to map Minecraft block IDs to Call of Duty: BO3 asset equivalents. This allows precise control over how each block should be interpreted in Radiant.

### 📝 Mapping Format

If a block is missing to your world it will appear as stone block, if it happens you can add a new line in the txt file.
Each line of the file must follow one of these formats:

- material    minecraft:<block_id>,<bo3_material_name>
- prefab      minecraft:<block_id>,<path_to_bo3_prefab.map>
- model       minecraft:<block_id>,"_model_"+<bo3_model_name>

