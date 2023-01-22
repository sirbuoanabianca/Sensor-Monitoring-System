import React from 'react'
import { BrowserRouter as Router, Switch } from 'react-router-dom'
import NavigationBar from './navigation-bar'
import AdminContainer from './client/admin-container'
import ClientContainer from './client/client-container'
import LoginContainer from './client/login-container'
import { GuardProvider, GuardedRoute } from 'react-router-guards';
import { Redirect } from 'react-router-dom'



import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';




const getIsLoggedIn = () => localStorage.getItem("isLoggedIn") === 'true';
const getIsAdmin = () => localStorage.getItem("isAdmin") === 'true';
const getCurrentClientId = () => localStorage.getItem("clientId");




const requireLogin = (to, from, next) => {
    if (to.meta.auth) {
        if (getIsLoggedIn()) {
            next();
        }
        next.redirect('/login');
    } else {
        next();
    }
};

const requireAdmin = (to, from, next) => {
    if (to.meta.isAdmin) {
        if (getIsAdmin()) {
            next();
        }
        next.redirect('/client');
    } else {
        next();
    }
};

function App() {

    return (
        <div className={styles.back}>
            <Router>
                <div>
                    <NavigationBar />

                    <GuardProvider guards={[requireLogin, requireAdmin]} >
                        <Switch>
                            <GuardedRoute
                                exact
                                path='/login'
                                render={() => <LoginContainer />}

                            />

                            <GuardedRoute
                                exact
                                path='/admin'
                                render={() => <AdminContainer />}
                                meta={{ auth: true, isAdmin: true }}
                            />

                            <GuardedRoute
                                exact
                                path='/client'
                                render={() => <ClientContainer currentClientId={getCurrentClientId()} />}
                                meta={{ auth: true, isAdmin: false }}
                            />

                            {/*Error*/}
                            <GuardedRoute
                                exact
                                path='/error'
                                render={() => <ErrorPage />}
                            />

                            {<GuardedRoute path='*' render={() => <Redirect to="/login" replace />} />}


                            <GuardedRoute render={() => <ErrorPage />} />
                        </Switch>

                    </GuardProvider>
                </div>
            </Router>
        </div>
    )

}

export default App
