class PredictionsRouter:
    """
    Redirige todos los modelos de appmodelo a la BD 'predictions'
    """
    def db_for_read(self, model, **hints):
        if model._meta.app_label == 'appmodelo':
            return 'predictions'
        return None

    def db_for_write(self, model, **hints):
        if model._meta.app_label == 'appmodelo':
            return 'predictions'
        return None

    def allow_relation(self, obj1, obj2, **hints):
        if (
            obj1._meta.app_label == 'appmodelo' or
            obj2._meta.app_label == 'appmodelo'
        ):
            return True
        return None

    def allow_migrate(self, db, app_label, model_name=None, **hints):
        """
        Solo migramos la app appmodelo en la BD 'predictions'
        """
        if app_label == 'appmodelo':
            return db == 'predictions'
        return None
