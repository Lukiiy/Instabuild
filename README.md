# Instabuild
> b1.7.3 CraftBukkit plugin

A kind of creative mode, but for beta.

## Commands

| Command                      |                          Description                           |     Permission |    Aliases     |
|:-----------------------------|:--------------------------------------------------------------:|---------------:|:--------------:|
| `instabuild`                 |                    Toggles instabuild mode                     | instabuild.use |      `ib`      |
| `instabuild list`            |                 Lists players using instabuild                 | instabuild.use |      `ib`      |
| `instabuild getid`           | Gets the ID of the item you're holding/block you're looking at | instabuild.use |      `ib`      |
| `instabuild listids`         |       Lists all avaliable Materials & IDs (without data)       | instabuild.use |      `ib`      |
| `igive <id:data> [amount]`   |                 Gives an item with custom data                 | instabuild.use | `ibg` `ibgive` |
| `igive <id> <data> [amount]` |                 Gives an item with custom data                 | instabuild.use | `ibg` `ibgive` |
| `pickblock`                  |        Gets a full stack of the block you're looking at        | instabuild.use |                |

### Why "Instabuild"?

This is the name Minecraft uses for the ability to not run out of blocks while placing them.

### How does it work?

Activating **/instabuild** gives you infinite resources, including unbreakable tools, and makes you invulnerable and untargetable by mobs. While active, you can also use a compass to teleport to the top of any block within 32 blocks.