## MotionLayout For Compose Examples
Sample project that demonstrates various uses of MotionLayout in Compose

## Overview
-----------------------------

### App Demo

#### Motion Layout as Collapsing toolbar for Column

This is based on using 
```kotlin
Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scroll)
    )
```

* [Code for Collapsing Toolbar over Column DSL Version](https://github.com/androidx/constraintlayout/blob/main/demoProjects/ExamplesComposeMotionLayout/app/src/main/java/com/example/examplescomposemotionlayout/CollapsingToolbarDsl.kt)
* [Code for Collapsing Toolbar over Column JSON Version](https://github.com/androidx/constraintlayout/blob/main/demoProjects/ExamplesComposeMotionLayout/app/src/main/java/com/example/examplescomposemotionlayout/CollapsingToolbarJson.kt)

https://user-images.githubusercontent.com/15019413/195418373-5a92e2b7-9ff1-4a8a-851e-09951557147b.mp4

#### Motion Layout as Collapsing toolbar for Lazy column

This is based on using
```kotlin
        Box(
            Modifier
                .fillMaxWidth()
                .nestedScroll(nestedScrollConnection)) {
            LazyColumn() {
                items(100) {
                    Text(text = "item $it", modifier = Modifier.padding(4.dp))
                }
            }
        }
```

* [Code for Collapsing Toolbar over Lazycolumn DSL Version](https://github.com/androidx/constraintlayout/blob/main/demoProjects/ExamplesComposeMotionLayout/app/src/main/java/com/example/examplescomposemotionlayout/CollapsingToolbarLazyDsl.kt)
* [Code for Collapsing Toolbar over Lazycolumn JSON Version](https://github.com/androidx/constraintlayout/blob/main/demoProjects/ExamplesComposeMotionLayout/app/src/main/java/com/example/examplescomposemotionlayout/CollapsingToolbarLazyJson.kt)

https://user-images.githubusercontent.com/15019413/195679372-153f6ccf-d263-4085-9441-c29c105360e7.mp4

#### Motion Layout in a Lazy column

This demostrates how to create a MotionLayout that is part of a lazycolumn

```Kotlin
    val maxPx = with(LocalDensity.current) { 250.dp.roundToPx().toFloat() }
    val minPx = with(LocalDensity.current) { 50.dp.roundToPx().toFloat() }
    val toolbarHeight = remember { mutableStateOf(maxPx) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val height = toolbarHeight.value;

                if (height + available.y > maxPx) {
                    toolbarHeight.value = maxPx
                    return Offset(0f, maxPx - height)
                }

                if (height + available.y < minPx) {
                    toolbarHeight.value = minPx
                    return Offset(0f, minPx - height)
                }

                toolbarHeight.value += available.y
                return Offset(0f, available.y)
            }

        }
    }

    val progress = 1 - (toolbarHeight.value - minPx) / (maxPx - minPx);
    Column {
      MotionLayout(....) { ....} 
        Box(
            Modifier
              .fillMaxWidth()
              .nestedScroll(nestedScrollConnection)) {
          LazyColumn() {
            items(100) {
              Text(text = "item $it", modifier = Modifier.padding(4.dp))
            }
          }
        }      
  
```

* [Code for MotionLayout in a Lazycolumn DSL Version](https://github.com/androidx/constraintlayout/blob/main/demoProjects/ExamplesComposeMotionLayout/app/src/main/java/com/example/examplescomposemotionlayout/MotionInLazyColumnDsl.kt)
* [Code for MotionLayout in a Lazycolumn JSON Version](https://github.com/androidx/constraintlayout/blob/main/demoProjects/ExamplesComposeMotionLayout/app/src/main/java/com/example/examplescomposemotionlayout/MotionInLazyColumnJson.kt)

![GraphInLazy](https://user-images.githubusercontent.com/15019413/195972547-c532fea8-08f0-4d71-acc6-059e438ea8fb.gif)

#### Motion Layout in a Lazy column animated computed graphs

The demo creates 100 graphs filled with random numbers in a LazyColumn

This demonstrates dynnamic creation of constraints to create a graph based on inputs. 
Creating a graph composable that is called:

```kotlin
  DynamicGraph(values = listOf<Float>(12f, 32f, 21f, 32f, 2f))
```

* [Graph in Lazy code](https://github.com/androidx/constraintlayout/blob/main/demoProjects/ExamplesComposeMotionLayout/app/src/main/java/com/example/examplescomposemotionlayout/DynamicGraph.kt)


![GraphInLazy](https://user-images.githubusercontent.com/15019413/195987030-29d2a656-26e9-4087-93e4-8e62e31ce73d.gif)
---------------
### Implement animation resulting from selection

This demos using the DSL to simply create complex interactions
[ReactionSelector.kt](https://github.com/androidx/constraintlayout/blob/main/demoProjects/ExamplesComposeMotionLayout/app/src/main/java/com/example/examplescomposemotionlayout/ReactionSelecor.kt) 

![emoji_selector_crop](https://user-images.githubusercontent.com/15019413/197297375-9a2463dc-8a40-48d7-8526-6b9424f9ec46.gif)


## Contributing

If you'd like to get involved and contribute please read [CONTRIBUTING](https://github.com/androidx/constraintlayout/blob/main/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

- **John Hoford** ([jafu888](https://github.com/jafu888))

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](https://github.com/androidx/constraintlayout/blob/main/LICENSE) file for details
