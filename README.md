# EzVirtualWorkbench

EzVirtualWorkbench is a production-ready convenience plugin by **EzInnovations** that opens vanilla utility menus with commands.

## Features
- Single-jar support from **Minecraft/Paper 1.14+** through modern Paper.
- Commands:
  - `/craft` (`/workbench`, `/wb`)
  - `/ec` (`/enderchest`) – opens the **real** Ender Chest inventory
  - `/anvil`
  - `/smithing`
  - `/grindstone`
  - `/loom`
  - `/stonecutter`
  - `/cartography`
- Includes per-command permissions, enable/disable toggles, cooldowns, disabled-world checks, optional sounds, and admin reload.

## Build
```bash
mvn clean package
```

Output jar name:
- `target/EzVirtualWorkbench-<version>.jar`

## Compatibility strategy (1.14 through modern Paper)
- Menu opening is centralized in `CompatibilityMenuOpenService`.
- It first attempts version-spanning `openX(Location, boolean)` methods via reflection.
- If unavailable, it falls back to container `InventoryType` opening (non-chest utility inventory types).
- `/ec` always opens `player.getEnderChest()` directly (real vanilla storage).

## Admin
- `/ezvirtualworkbench reload`
- Permission: `ezvirtualworkbench.admin.reload`

## Future v2 safe additions
- Add support for additional vanilla utility containers (enchanting, brewing, furnaces) via new `MenuType` entries.
- Add per-command sounds/messages.
- Add granular world restrictions per command.
- Add optional metrics and debug mode for compatibility diagnostics.
