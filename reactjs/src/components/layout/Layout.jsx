import React from 'react'
import MainNavigation from './MainNavigation'
import Container from 'react-bootstrap/Container'

const Layout = (props) => {

  return (
    <>
      <MainNavigation />
      <Container>
        <main className="pt-5">
          {props.children}
        </main>
      </Container>
    </>
  )
}

export default Layout