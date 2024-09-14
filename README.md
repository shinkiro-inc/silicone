# Silicone

Very opinionated Gradle plugin revolving around creating development environments for Minecraft.

#### Let There Be Dragons

This project is EXTREMELY unfinished. I throw out chunks of the codebase on a whim to rewrite them. Don't expect anything to stay as it is, don't expect anything to work.

## Why?

I looked through multiple existing plugins, none really achieved what I needed, including:

### Existing solutions

#### Architectury Loom

Lack of support for legacy versions.

#### Essential Loom

Based on Architectury Loom, however provides support for legacy versions. This all sounds great, however it relies on preprocessor (I'll get into why that's an issue later in this rant of a readme.)

#### Deftu Gradle Toolkit

Would have been a good option, if it didn't also rely on preprocessor, on top of being a generally annoying-to-work-with mess (coincidentally, just like its developer).

### Preprocessor

First, a broad explanation of how preprocessor works, for those unaware. 

Preprocessor remaps the source of the project right before compiling, to whatever the acceptable source-code mappings for a given loader are.

#### The Problem(s)

1. Preprocessor isn't exactly *good* at its job. It can miss things, it can get things wrong, it requires an explicit `this` at times, the list goes on.
2. Preprocessor is **slow**. LiquidBounce's Legacy branch (which this plugin is intended for), at time of writing this, has over 500 Kotlin files, and around 100 Java files, many of them massive. It takes almost a full minute, on a high speed system and on an M.2 SSD, for preprocessor to remap the source.

### The solution

The goal of this project, to fix this mess by starting from scratch. No loom, no nothing.

## How?

In this stage of the project, too much is changing constantly to give an accurate explanation that will stand the test of time, but as soon as the project reaches a usable state, this section will be done.

## Contributing

If you want to help, contact me on Discord if you can (my friend requests and DMs are off, so only possible if we are already friends), otherwise make an issue saying so, and I'll get in contact with you.