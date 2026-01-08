# 1. The Interface

The terminal is split into three distinctive panels:

## Top Left: The Process Queue

Shows your incoming tickets with a countdown timer (TTL)\
Example: `[PID xxx] GET /Burger (TTL: 15s)`

## Top Right: The RAM Sticky-Notes

This acts as your inventory. \
You have 4 slots of memory. \
Shows what ingredients are currently loaded and ready to use. \
Example: `[0x01]: Patty, [0x02]: Bun, [0x03]: <EMPTY>, [0x04]: <EMPTY>`.

## Bottom: The Terminal

To actually progress through orders and call commands, use the terminal \
Example: `{USER}@kernelkitchen:~$ ls pantry`

# 2. The Flow

## Requests

Start with getting an order in the Process Queue \
If your queue is full (greater than 5 pending orders), then the kitchen crashes!

## Fetching

Ingredients live on the Disk. You don't have them yet. \
You need to fetch the disk so that you can load it into your RAM \
`fetch [ingredient]` \
**However, fetching isn't instant.** It takes some time for you to load it into RAM. \
While fetching, you cannot type in your terminal. Make your actions count. \
A progress bar will be shown for your convenience for the first couple rounds. As the difficulty increases, your keyboard will unlock, but you are blocked from execution.

## Processing

Some recipies required cooked ingredients (e.g., a Burger needs a `COOKED` Patty, not a `RAW` one).
You must cook it via tha `make` command and the target address (e.g., `make 0x01`)
It takes 2 seconds for you to cook sometime, and it transitions between `RAW` -> `COOKED`
*Warning*: If you `make` something that is already `COOKED`, it becomes `BURNT` (and unusable)

## Management

Once fetched, it appears in your RAM. \
Since you only have 4 slots, you must be careful not to go over your inventory space.
- If you try to `fetch` when your RAM is full -> **Stack Overflow Error** 
  - Stack Overflow Errors result in a loss of 5 seconds of your controls while the system reboots.
  - You must `free` items to make space if you fetched the wrong thing.

## Compiling 

This is where you actually start cooking \n
To finish and order, you use the `compile` command with the Order ID for that process. \
The game doesn't automatically check if the exact required ingredients are in your RAM... \
The compilation processes checks one by one to see if you have the required ingredients.

## Checking

Two things can happen when you're compiling:
- *Correct*: Everything works ok! You have everything, the order disappears, you gain Score (Uptime), and the ingredients are removed from RAM automatically
- *Incorrect*: A compilation error occurred. You're missing an ingredient.
  - Output: `Compilation Error: Missing Dependency [Missing Ingredient]`

# 3. Advanced Mechanics

## Bit Corruption

If an ingredient sits in RAM for more than 20 seconds without being used, it becomes **CORRUPTED**. \n
A corrupted item cannot be used. It turns red in your UI to indicate this. \n
The more corrupted items you have in RAM, the faster the corruption process takes. \n
You manually must run `gc {ADDRESS}` to clear it out before you can use that slot again.

## System Integrity (HP) vs. Uptime (Score)
Uptime: This is your high score. As long as you're online, it goes up by +1 every second.
System Integrity: This is your health. It starts at 100%.
Deductions are as follows:
- -10% for a failed compile command
- -5% for a dropped packet (Order TTL expires)
-  +10% for a successful serve

# 4. The Command List
These are commands the player needs to memorize and control the flow of the game

| Command   |      Usage       |                                                                                                                  Effect |
|:----------|:----------------:|------------------------------------------------------------------------------------------------------------------------:|
| `ls`      |   `ls pantry`    |                                                                                             Lists available ingredients |
| `fetch`   |  `fetch patty`   |                                                          Downloads ingredient int first open RAM slot. Takes 2 seconds. |
| `compile` | `compile [PID]`  |                                                        Attempts to cook the dish for the Order ID (e.g., `compile 102`) |
| `free`    | `free [address]` |                                                   Manually deletes an item from a specific RAM slot (e.g., `free 0x01`) |
| `gc`      |  `gc [address]`  |                                                The Manual Garbage Collector TM. Clears the corrupted RAM from that slot |
| `man`     |   `man [dish]`   | The Good 'Ol Manual TM. Shows the recipie (dependencies) for a dish. (e.g, `man burger` -> Dependencies: `Bun`, `Patty` |
| `make`    | `make [address]` |                    Transition your food states it just under two seconds! Transitions from `RAW` -> `COOKED` -> `BURNT` |


# 5. Difficulty Progression

I plan to add some sort of progression. I don't know what yet.
Higher levels might just be more annoying (Bit corruption introduced, custom events, speed changes, etc.)

My thoughts right now:
As `Uptime` increases, the System load increases level by level
No events in the beginning, but mechanics are slowly introduced (Like corruption) at round 1+
Fetch times might take longer, More constraints.

# 6. Winning & Losing

Objective: Maximize your `Uptime` 
System Integrity: Starts at 100%

Game over conditions
- *Queue Overflow*: 6 pending orders at once (You overloaded your server)
- *Kernel Panic*: A critical (VIP process, indicated) wasn't served.
- *System Integrity*: It reaches zero.