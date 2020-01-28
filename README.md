# JavaRouter
Java autorouter for EAGLE PCB CAD

**IN PROGRESS**

This project is an autorouter for EAGLE PCB files. An autorouter is a program that intelligently routes a PCB given parameters and tries its best to ensure a functional PCB. However, autorouters should never be trusted and a human should always review the board and fix any errors.

Features I'm working on:
- Read schematic and board XML and store it in memory in a registry for easy and straightforward access
- Detect signal names and apply appropriate constraints based on default values (user can change these for specific nets)
- Visual output of autorouter progress
- Custom scripting and autorouting algorithms/rules
- Output as EAGLE board XML. Does not modify schematic!
- DRC error output and detection
