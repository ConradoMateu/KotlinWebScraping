package base

interface UseCase<out T>  {
    fun execute(): T
}