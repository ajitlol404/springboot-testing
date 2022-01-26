# springboot-testing

JUNIT TESTS IN BDD STYLES:
_________________________
Syntax:-

    @Test
    public void given_when_then()
    {
    
        //given- precondition or setup

        //when- action or the behaviour we're testing

        //then- verify the output

    }

MOCKITO:-
__________

mock() method :- 

To create a mock object of a given class or interface.

@InjectMocks

When we want to inject a mocked object into another mocked object.
It creates the mock object of the class and inject the mocks that are marked with the annotation @Mock into it.
