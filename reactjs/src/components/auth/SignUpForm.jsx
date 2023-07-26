import React from 'react'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'

const SignUpForm = () => {
  return (
    <section className="d-flex justify-content-center align-content-center">
      <Form className="card w-50 p-5 bg-light">
        <Form.Group className="text-center mb-3">
          <h2>Sign up</h2>
          <p className="mt-2">Sign up for membership to use the service.</p>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control type="email" placeholder="Enter email" />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control type="password" placeholder="Password" />
        </Form.Group>
        <Button variant="primary" type="submit">Create Account</Button>
        <a class="link-secondary link-underline link-underline-opacity-0 mt-3 m-auto" href="/signin"><small>Already have an account? <b>Sign in</b></small></a>
      </Form>
    </section>
  )
}

export default SignUpForm