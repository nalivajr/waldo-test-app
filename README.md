# Wald Test App
This is the repository for small test app

## Notes

App is built using
- Koin
- Coroutines
- AnroidX and arch components
- Android Paging
- MVVM and Clean Architecture were used
- Apollo as a GraphQl client
- Android Navigation component
- Picasso for image loading

For test purpose on login page please do long press on login button to fill form with the test credentials

## Domain layer

Coroutines are used to execute complex operations and data loading. Mainly, they are used as 
`viewModelScope.launch` (e.g. `LoginViewModel`) to start some operation. Once operation is ready,
result is processed (e.g. dispatch errors) and posted to UI.
If you see into `WaldoAPI` and `WaldoApiImpl` you may notice, that functions are not marked as
`suspend` and inside `WaldoApiImpl` operations are executed in `runBlocking`. That was done in order to
keep different application components as a simple classes, and do not tie with coroutines technology.
This allows later to replace application components and does not makes to use particular technology. 
So, in general we can reuse them in simple Java code. That's why interfaces in the app and the general idea 
is to execute code synchronously and use asynchronous operation only where they are needed or inside
some particular implementation. So, in two words - interfaces looks like app is synchronous, but implementation
can be asynchronous. 

## Error handling

Application has simple error handing. The most common network and validation errors are 
handled. All other errors are considered as unexpected. 
The main idea is to throw exception of particular type if app faces with a problem,
which then are translated to user friendly message using `ErrorDispatcher` (mostly in View models)

## Tests

Unfortunately because of restricted time, unit tests were not added
But since clean arhitecure is used, different components of the app are represented with the interfaces, 
so we can easily Mock them with Mockito framework and check.

Apps designed to allow to cover all the domain logic (network, domain services (or repositories), etc.) 
with usual unit tests, and only UI part requires other approach for testing (if needed).

## Next steps

For improving app, next is need to be done
1. Add unit tests
2. Save token for active user in prefs (or even secure prefs) and open album if session is active
3. Add action bar with menu to log user out
4. Add placeholder if image failed to load
5. Right now app load medium2x thumbnails, but it's possible to configure based on device screen size
or other rules
